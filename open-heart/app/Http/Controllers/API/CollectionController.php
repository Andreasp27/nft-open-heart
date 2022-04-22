<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use App\Http\Resources\CollectionResource;
use App\Models\Daftarbid;
use App\Models\History;
use App\Models\Historywallet;
use App\Models\Koleksi;
use App\Models\Trending;
use App\Models\User;
use App\Models\Wallet;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class CollectionController extends Controller
{
    public function index()
    {
        $koleksi = Koleksi::latest()->get();
        return response()
            ->json($koleksi);
    }

    public function cariKoleksi(Request $request)
    {
        $koleksi = Koleksi::where('id', $request->id)->get();

        return response()
            ->json([CollectionResource::collection($koleksi)]);
    }

    public function trending()
    {
        $koleksi = Koleksi::with('trending')->join('trendings', 'koleksis.id', '=', 'trendings.koleksi_id')
            ->select('koleksis.*', 'trendings.kenaikan');

        $koleksi = $koleksi->orderByDesc('trendings.kenaikan')->get();

        return response()
            ->json($koleksi);
    }

    public function showById()
    {
        $koleksi = Koleksi::where('user_id', auth()->user()->id)->latest()->get();
        return response()
            ->json([CollectionResource::collection($koleksi)]);
    }

    public function create(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'nama_item' => 'required',
            'harga' => 'required',
            'image_path' => 'required',
            'deskripsi' => 'required',

        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors());
        }

        $imageName = time() . '.' . $request->image_path->extension();
        $request->image_path->move(public_path('images'), $imageName);

        $path = 'images/' . $imageName;

        $koleksi = Koleksi::Create([
            'pembuat' => auth()->user()->name,
            'nama_item' => $request->nama_item,
            'harga' => $request->harga,
            'image_path' => $path,
            'deskripsi' => $request->deskripsi,
            'status' => 'Created',
            'user_id' => auth()->user()->id,
        ]);

        History::create([
            'nama' => auth()->user()->name,
            'harga' => $request->harga,
            'aksi' => 'mint',
            'koleksi_id' => $koleksi->id,
        ]);

        Trending::create([
            'kenaikan' => 0,
            'koleksi_id' => $koleksi->id,

        ]);

        return response()
            ->json(['message' => 'success']);
    }

    public function bid(Request $request)
    {

        $validator = Validator::make($request->all(), [
            'jumlah_bid' => 'required',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors());
        }

        $koleksi = Koleksi::find($request->id_koleksi);
        $saldoUser = auth()->user()->wallet->saldo;

        if ($saldoUser < $request->jumlah_bid) {
            return response()
                ->json(['msg' => 'Bid failed']);
        }

        if ($request->jumlah_bid <= $koleksi->harga) {
            return response()
                ->json(['msg' => 'Bid failed']);
        }
        Daftarbid::create([
            'harga_bid' => $request->jumlah_bid,
            'nomor_user' => auth()->user()->nomor_user,
            'koleksi_id' => $request->id_koleksi,
        ]);

        $koleksi->update([
            'harga' => $request->jumlah_bid,
        ]);

        return response()
            ->json(['msg' => 'Bid success']);
    }

    public function terimaBid(Request $request)
    {

        $user = User::firstWhere('nomor_user', $request->nomor_user);
        $koleksi = Koleksi::find($request->id_koleksi);
        if ($user->wallet->saldo < $koleksi->harga) {
            return response()
                ->json(['msg' => 'Purchase failed']);
        }

        //koleksi
        $koleksi->update([
            'user_id' => $user->id,
            'status' => 'Collected',
        ]);

        //trending
        $persen = (($koleksi->harga - $koleksi->history->last()->harga) / $koleksi->history->last()->harga) * 100;

        //history koleksi
        History::create([
            'nama' => $user->name,
            'harga' => $koleksi->harga,
            'aksi' => 'Purchased',
            'koleksi_id' => $koleksi->id,
        ]);



        //wallet yang beli
        Wallet::where('user_id', $user->id)->update([
            'saldo' => $user->wallet->saldo - $koleksi->harga,
        ]);

        //history wallet yang beli
        Historywallet::create([
            'jumlah' => $koleksi->harga,
            'status' => 'Keluar',
            'wallet_id' => $user->wallet->id,
        ]);

        //wallet penerima 
        Wallet::where('user_id', auth()->user()->id)->update([
            'saldo' => auth()->user()->wallet->saldo + $koleksi->harga,
        ]);

        //history penerima
        Historywallet::create([
            'jumlah' => $koleksi->harga,
            'status' => 'Masuk',
            'wallet_id' => auth()->user()->wallet->id,
        ]);



        Trending::where('koleksi_id', $koleksi->id)->update([
            'kenaikan' => $persen,
        ]);

        //daftar bid
        Daftarbid::where('koleksi_id', $koleksi->id)->delete();

        return response()
            ->json(['msg' => 'Purchase Success']);
    }

    public function updateBid(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'jumlah_bid' => 'required',
        ]);

        if ($request->jumlah_bid > auth()->user()->wallet->saldo) {
            return response()
                ->json(['msg' => 'Bid Failed']);
        }

        $koleksi = Koleksi::find($request->id_koleksi);

        $koleksi->daftarbid->firstWhere('nomor_user', auth()->user()->nomor_user)->update([
            'harga_bid' => $request->jumlah_bid,
        ]);

        $koleksi->update([
            'harga' => $request->jumlah_bid,
        ]);

        return response()
            ->json(['msg' => 'Bid Success']);
    }
}
