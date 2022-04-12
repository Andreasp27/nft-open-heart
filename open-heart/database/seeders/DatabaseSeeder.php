<?php

namespace Database\Seeders;

use App\Models\User;
use App\Models\Wallet;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        User::create([

            'name' => 'Bambang',
            'nomor_user' => "USR0001",
            'email' => 'bambang123@gmail.com',
            'jenis_kelamin' => 'Laki - Laki',
            'alamat' => 'Jl. Diponegoro No. 5',
            'nomor_telepon' => '0822222314',
            'bio' => "Anak Pandai",
            'suka' => 0,
            'gambar_path' => "",
            'banner_path' => "",
            'password' => bcrypt('bambang123'),
        ]);

        Wallet::create([
            'saldo' => 0,
            'user_id' => 1,
        ]);
    }
}
