package db

type AuthorEntity struct {
	Id   int64  `db:"id"`
	Name string `db:"name"`
}

type BookEntity struct {
	Id            int64  `db:"id"`
	Title         string `db:"title"`
	Number        int64  `db:"number"`
	Volume        string `db:"volume"`
	Authors       []AuthorEntity
	GenreId       int64  `db:"genre_id"`
	GenreName     string `db:"genre_name"`
	EndowmentId   int64  `db:"endowment_id"`
	EndowmentName string `db:"endowment_name"`
	Manuscript    bool   `db:"manuscript"`
	Print         bool   `db:"print"`
	Comment       string `db:"comment"`
	EditorComment string `db:"editor_comment"`
}

type GenreEntity struct {
	Id   int64  `db:"id"`
	Name string `db:"name"`
}

type EndowmentEntity struct {
	Id   int64  `db:"id"`
	Name string `db:"name"`
}
