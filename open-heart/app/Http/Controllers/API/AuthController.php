<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use App\Models\User;
use App\Models\Wallet;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Redis;
use Illuminate\Support\Facades\Validator;

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
            ->json(['message' => 'Hi ' . $user->name . ', welcome to home', 'access_token' => $token, 'token_type' => 'Bearer',]);
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
            $request->gambar_path->move(public_path('images'), $imageName);

            $image_path = 'images/' . $imageName;
        } else {
            $image_path = auth()->user()->gambar_path;
        }

        if ($request->banner_path != null) {
            $imageBanner = time() + 1 . '.' . $request->banner_path->extension();
            $request->banner_path->move(public_path('images'), $imageBanner);

            $image_banner_path = 'images/' . $imageBanner;
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
            ->json(['data' => User::find(auth()->user()->id)]);
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
        $user = User::find($request->id)->update([
            'suka' => DB::Raw('suka+1'),
        ]);

        return response()
            ->json(['msg' => 'liked']);
    }
}
