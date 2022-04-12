<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Historywallet extends Model
{
    protected $guarded = [];
    use HasFactory;
    public function wallet()
    {
        return $this->belongsTo(Wallet::class);
    }
}
