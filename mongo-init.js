db.createUser(
        {
            user: "admin-dic",
            pwd: "admin",
            roles: [
                {
                    role: "readWrite",
                    db: "data-in-cloud"
                }
            ]
        }
);
