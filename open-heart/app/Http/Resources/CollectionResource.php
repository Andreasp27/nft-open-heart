<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class CollectionResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array|\Illuminate\Contracts\Support\Arrayable|\JsonSerializable
     */
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'pembuat' => $this->pembuat,
            'nama_item' => $this->nama_item,
            'harga' => $this->harga,
            'image_path' => $this->image_path,
            'deskripsi' => $this->deskripsi,
            'status' => $this->status,
            'user_id' => $this->user_id,
            'pemilik' => $this->user->name,
            'trending_id' => $this->trending_id,
            'created_at' => $this->created_at,
            'updated_at' => $this->updated_at,
            'history' => $this->history,
            'trending' => $this->trending,
            'daftarbid' => $this->daftarbid,

        ];
    }
}
