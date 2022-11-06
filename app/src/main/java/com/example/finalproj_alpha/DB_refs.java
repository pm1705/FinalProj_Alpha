package com.example.finalproj_alpha;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DB_refs {
    public static StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();

    public static DatabaseReference refRequests=FBDB.getReference("Requests");
}
