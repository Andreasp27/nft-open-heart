<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

Route::post('/register', [App\Http\Controllers\API\AuthController::class, 'register']);
//API route for login user
Route::post('/login', [App\Http\Controllers\API\AuthController::class, 'login']);

//Protecting Routes
Route::group(['middleware' => ['auth:sanctum']], function () {
    Route::get('/profile', function (Request $request) {
        return auth()->user();
    });
    Route::post('/update', [App\Http\Controllers\API\AuthController::class, 'data']);
    Route::post('/updateimg', [App\Http\Controllers\API\AuthController::class, 'updateImg']);
    Route::post('/updatepass', [App\Http\Controllers\API\AuthController::class, 'updatePass']);
    Route::post('/wallet/topup', [App\Http\Controllers\API\WalletController::class, 'topUpWallet']);
    Route::post('/wallet/send', [App\Http\Controllers\API\WalletController::class, 'sendWallet']);
    Route::get('/wallet', [App\Http\Controllers\API\WalletController::class, 'index']);
    Route::get('/wallet/history', [App\Http\Controllers\API\WalletController::class, 'history']);

    Route::get('/collection', [App\Http\Controllers\API\CollectionController::class, 'index']);
    Route::get('/collection/mycollection', [App\Http\Controllers\API\CollectionController::class, 'showById']);
    Route::post('/collection/create', [App\Http\Controllers\API\CollectionController::class, 'create']);
    Route::post('/collection/item', [App\Http\Controllers\API\CollectionController::class, 'cariKoleksi']);
    Route::post('/collection/bid', [App\Http\Controllers\API\CollectionController::class, 'bid']);
    Route::post('/collection/terimabid', [App\Http\Controllers\API\CollectionController::class, 'terimaBid']);
    Route::post('/collection/updatebid', [App\Http\Controllers\API\CollectionController::class, 'updateBid']);


    // API route for logout user
    Route::post('/logout', [App\Http\Controllers\API\AuthController::class, 'logout']);
});
