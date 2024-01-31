db.createUser(
    {
        user: "admin",
        pwd: "1234",
        roles: [
            {
                role: "readWrite",
                db: "twitter_clone_db"
            }
        ]
    }
);

db.createCollection('Users');
db.createCollection('Posts');