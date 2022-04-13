<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use App\Http\Resources\WalletResource;
use App\Models\Historywallet;
use App\Models\User;
use App\Models\Wallet;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;

class WalletController extends Controller
{
    public function index()
    {
        $saldo = Wallet::where('id', auth()->user()->wallet->id)->latest()->get();
        return response()
            ->json([WalletResource::collection($saldo)]);
    }
    public function topUpWallet(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'saldo' => 'required',
            'password' => 'required|string',

        ]);
        if ($validator->fails()) {
            return response()->json($validator->errors());
        }

        $old_saldo = Wallet::find(auth()->user()->wallet->id)->saldo;
        $pass = auth()->user()->password;
        if (Hash::check($request->password, $pass)) {
            Wallet::find(auth()->user()->wallet->id)->update([
                'saldo' => $old_saldo + $request->saldo,
            ]);

            Historywallet::create([
                'jumlah' => $request->saldo,
                'status' => 'masuk',
                'wallet_id' => auth()->user()->wallet->id
            ]);


            return response()
                ->json(['msg' => 'Top up success', 'data' => Wallet::find(auth()->user()->wallet->id)]);
        } else {
            return response()
                ->json(['msg' => 'Top up failed']);
        }
    }

    public function sendWallet(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'nomor_user' => 'required|string',
            'saldo' => 'required',
            'password' => 'required|string',
        ]);

        $nomor_user = User::firstWhere('nomor_user', $request->nomor_user);

        $old_saldoUser = auth()->user()->wallet->saldo;
        if ($old_saldoUser < $request->saldo) {
            return response()
                ->json(['msg' => 'Send failed']);
        }
        $old_saldo = $nomor_user->wallet->saldo;
        $pass = auth()->user()->password;
        if (Hash::check($request->password, $pass)) {
            Wallet::find($nomor_user->wallet->id)->update([
                'saldo' => $old_saldo + $request->saldo,
            ]);

            // user yg nerima
            Historywallet::create([
                'jumlah' => $request->saldo,
                'status' => 'Masuk',
                'wallet_id' => Wallet::find($nomor_user->wallet->id)->id,
            ]);



            Wallet::find(auth()->user()->wallet->id)->update([
                'saldo' => $old_saldoUser - $request->saldo,
            ]);

            //user yg send
            Historywallet::create([
                'jumlah' => $request->saldo,
                'status' => 'Keluar',
                'wallet_id' => auth()->user()->wallet->id,
            ]);



            return response()
                ->json(['msg' => 'Send successss', 'data' => Wallet::find(auth()->user()->wallet->id)]);
        } else {
            return response()
                ->json(['msg' => 'Send failed']);
        }
    }
}
