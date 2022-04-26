<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use App\Models\Images;
use App\Models\Like;
use App\Models\User;
use App\Models\Wallet;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Redis;
use Illuminate\Support\Facades\Validator;

use function PHPUnit\Framework\isNull;

class AuthController extends Controller
{
    public function register(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'name' => 'required|string|max:255',
            'email' => 'required|string|email|max:255|unique:users',
            'password' => 'required|string|min:8'
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors());
        }

        $user = User::create([
            'name' => $request->name,
            'email' => $request->email,
            'password' => Hash::make($request->password),
        ]);
        $user->update([
            'nomor_user' => "USR" . str_pad($user->id, 4, '0', STR_PAD_LEFT),
        ]);



        $wallet = Wallet::create([
            'saldo' => 0,
            'user_id' => $user->id,
        ]);

        // $token = $user->createToken('auth_token')->plainTextToken;

        return response()
            ->json(['data' => $user, 'wallet' => $wallet]);
    }

    public function login(Request $request)
    {
        if (!Auth::attempt($request->only('email', 'password'))) {
            return response()
                ->json(['message' => 'Unauthorized'], 401);
        }

        $user = User::where('email', $request['email'])->firstOrFail();

        $token = $user->createToken('auth_token')->plainTextToken;

        return response()
            ->json(['id' => $user->id, 'message' => 'Hi ' . $user->name . ', welcome to home', 'access_token' => $token, 'token_type' => 'Bearer',]);
    }

    // method for user logout and delete token
    public function logout()
    {
        auth()->user()->tokens()->delete();

        return [
            'message' => 'You have successfully logged out and the token was successfully deleted'
        ];
    }

    public function data(Request $request)
    {

        $validator = Validator::make($request->all(), [
            'name' => 'required|string',
            'email' => 'required|string',
            'jenis_kelamin' => 'required|string',
            'alamat' => 'required|string',
            'nomor_telepon' => 'required|string',
            'bio' => 'required|string',
            'gambar_path' => 'nullable|image|mimes:jpg,png,jpeg,gif,svg',
            'banner_path' => 'nullable',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors());
        }

        if ($request->gambar_path != null) {
            $imageName = time() . '.' . $request->gambar_path->extension();
            $request->gambar_path->move(public_path('images/' . auth()->user()->nomor_user . "/profile"), $imageName);

            $image_path = 'images/' . auth()->user()->nomor_user . "/profile/" . $imageName;

            $md5image = md5(file_get_contents($image_path));

            if (Images::where('user_id', '!=', auth()->user()->id)->get()->contains("mdigest", $md5image)) {
                unlink($image_path);
                return response()
                    ->json(['message' => 'image failure']);
            }

            Images::create([
                'mdigest' => $md5image,
                'user_id' => auth()->user()->id,
            ]);
        } else {
            $image_path = auth()->user()->gambar_path;
        }

        if ($request->banner_path != null) {
            $imageBanner = time() + 1 . '.' . $request->banner_path->extension();
            $request->banner_path->move(public_path('images/' . auth()->user()->nomor_user . "/profile"), $imageBanner);

            $image_banner_path = 'images/' . auth()->user()->nomor_user . "/profile/" . $imageBanner;

            $md5banner = md5(file_get_contents($image_banner_path));

            if (Images::where('user_id', '!=', auth()->user()->id)->get()->contains("mdigest", $md5banner)) {
                unlink($image_banner_path);
                return response()
                    ->json(['message' => 'image failure']);
            }

            Images::create([
                'mdigest' => $md5banner,
                'user_id' => auth()->user()->id,
            ]);
        } else {
            $image_banner_path = auth()->user()->banner_path;
        }

        $user = User::firstWhere('id', auth()->user()->id)->update([
            'name' => $request->name,
            'email' => $request->email,
            'jenis_kelamin' => $request->jenis_kelamin,
            'alamat' => $request->alamat,
            'nomor_telepon' => $request->nomor_telepon,
            'bio' => $request->bio,
            'gambar_path' => $image_path,
            'banner_path' => $image_banner_path,
        ]);

        return response()
            ->json(['message' => 'success']);
    }

    public function updateImg(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'gambar_path' => 'nullable|image|mimes:jpg,png,jpeg,gif,svg',
            'banner_path' => 'nullable|image|mimes:jpg,png,jpeg,gif,svg',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors());
        }


        if ($request->gambar_path != null) {
            $imageName = time() . '.' . $request->gambar_path->extension();
            $request->gambar_path->move(public_path('images'), $imageName);

            $image_path = 'images/' . $imageName;
        } else {
            $image_path = auth()->user()->gambar_path;
        }


        User::firstWhere('id', auth()->user()->id)->update([
            'gambar_path' => $image_path,
            'banner_path' => $image_path,
        ]);

        return response()
            ->json(['data' => User::find(auth()->user()->id)]);
    }

    public function updatePass(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'last_password' => 'required|string',
            'password' => 'required|string',
            'confirm_password' => 'required|string| same:password'
        ]);

        $oldpass = auth()->user()->password;
        $validatedData['password'] = bcrypt($request->password);
        if (Hash::check($request->last_password, $oldpass)) {
            User::where('id', auth()->user()->id)
                ->update($validatedData);
            return response()
                ->json(['message' => 'success']);
        } else {
            return response()
                ->json(['message' => 'failed']);
        }
    }

    public function suka(Request $request)
    {
        $user = User::find($request->id);
        $jml_suka = $user->suka;


        $id_like = $user->like->firstWhere('liked_by', auth()->user()->id);

        if (is_null($id_like)) {
            Like::create([
                'liked_by' => auth()->user()->id,
                'user_id' => $request->id,

            ]);
            $jml_suka++;
            $msg = "liked";
        } else {
            Like::find(($id_like)->id)->delete();
            $jml_suka--;
            $msg = "unliked";
        }

        $user->update([
            'suka' => $jml_suka,
        ]);


        return response()
            ->json(['message' => $msg]);
    }

    public function getAllUser()
    {
        $user = User::orderByDesc('suka')->get();
        return response()
            ->json($user);
    }

    public function getAllCreator(Request $request)
    {
        $user = User::with('koleksi', 'like')->find($request->id);
        return response()
            ->json($user);
    }
}
