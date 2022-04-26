<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Koleksi extends Model
{
    protected $guarded = [];
    use HasFactory;
    public function user()
    {
        return $this->belongsTo(User::class);
    }
    public function history()
    {
        return $this->hasMany(History::class);
    }
    public function trending()
    {
        return $this->hasOne(Trending::class);
    }
    public function daftarbid()
    {
        return $this->hasMany(Daftarbid::class);
    }
    public function images()
    {
        return $this->hasOne(Images::class);
    }
}
