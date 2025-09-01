package db

type Author struct {
	Id   int64  `db:"id"`
	Name string `db:"name"`
}

type Book struct {
	Id    int64  `db:"id"`
	Title string `db:"title"`
}

type Genre struct {
	Id   int64  `db:"id"`
	Name string `db:"name"`
}

type Endowment struct {
	Id   int64  `db:"id"`
	Name string `db:"name"`
}
