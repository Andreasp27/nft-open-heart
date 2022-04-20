<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use App\Http\Resources\HistoryResource;
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
        $saldo = Wallet::find(auth()->user()->wallet->id);
        return response()
            ->json($saldo);
    }
    public function history()
    {
        $saldo = Historywallet::where('wallet_id', auth()->user()->wallet->id)->latest()->get();
        return response()
            ->json($saldo);
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
                ->json(['message' => 'success']);
        } else {
            return response()
                ->json(['message' => 'failed']);
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
                ->json(['message' => 'failed_1']);
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
                'status' => 'masuk',
                'wallet_id' => Wallet::find($nomor_user->wallet->id)->id,
            ]);



            Wallet::find(auth()->user()->wallet->id)->update([
                'saldo' => $old_saldoUser - $request->saldo,
            ]);

            //user yg send
            Historywallet::create([
                'jumlah' => $request->saldo,
                'status' => 'keluar',
                'wallet_id' => auth()->user()->wallet->id,
            ]);

            return response()
                ->json(['message' => 'success']);
        } else {
            return response()
                ->json(['message' => 'failed']);
        }
    }
}
