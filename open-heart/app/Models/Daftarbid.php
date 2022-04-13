<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Daftarbid extends Model
{
    protected $guarded = [];
    use HasFactory;
    public function koleksi()
    {
        return $this->belongsTo(Koleksi::class);
    }
}
