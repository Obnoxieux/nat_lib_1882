package db

import (
	"github.com/pocketbase/dbx"
)

type BookRepository interface {
	GetSingleBookByID(id int64) (BookEntity, error)
	GetFilteredBooks(filter BooksFilter) ([]BookEntity, error)
}

type BooksFilter struct {
	Author     int64
	Genre      int64
	Endowment  int64
	Manuscript bool
	Print      bool
	Search     string
	Limit      int64
	Offset     int64
}

type PostgresBookRepository struct {
	DB *dbx.DB
}

func (repo PostgresBookRepository) GetSingleBookByID(id int64) (BookEntity, error) {
	var book BookEntity
	query := repo.DB.
		Select("*").
		From(BookTable).
		Where(dbx.NewExp("id = {:id}", dbx.Params{"id": id}))

	err := query.One(&book)
	if err != nil {
		return book, err
	}
	return book, nil
}

func (repo PostgresBookRepository) GetFilteredBooks(filter BooksFilter) ([]BookEntity, error) {
	query := repo.getBookBaseQuery()
	var books []BookEntity

	if filter.Author > 0 {
		query.AndWhere(dbx.NewExp("ab.author_id = {:author}", dbx.Params{"author": filter.Author}))
	}

	if filter.Genre > 0 {
		query.AndWhere(dbx.NewExp("b.genre = {:genre}", dbx.Params{"genre": filter.Genre}))
	}

	if filter.Endowment > 0 {
		query.AndWhere(dbx.NewExp("b.endowment = {:endowment}", dbx.Params{"endowment": filter.Endowment}))
	}

	if filter.Manuscript {
		query.AndWhere(dbx.NewExp("b.manuscript = {:manuscript}", dbx.Params{"manuscript": true}))
	}

	if filter.Print {
		query.AndWhere(dbx.NewExp("b.print = {:print}", dbx.Params{"print": true}))
	}

	if filter.Search != "" {
		params := dbx.Params{"search": "%" + filter.Search + "%"}
		query.AndWhere(dbx.Or(
			dbx.NewExp("b.title LIKE {:search}", params),
			dbx.NewExp("b.comment LIKE {:search}", params),
			dbx.NewExp("b.editor_comment LIKE {:search}", params),
			dbx.NewExp("a.name", params),
		))
	}

	if filter.Limit != 0 {
		query.Limit(filter.Limit)
	}

	if filter.Offset != 0 {
		query.Offset(filter.Offset)
	}

	err := query.All(&books)
	if err != nil {
		return books, err
	}

	return books, nil
}

func (repo PostgresBookRepository) getBookBaseQuery() *dbx.SelectQuery {
	query := repo.DB.
		Select(
			"b.id",
			"b.title",
			"b.number",
			"b.volume",
			"b.genre",
			"g.name AS genre_name",
			"b.endowment",
			"e.name AS endowment_name",
			"b.manuscript",
			"b.print",
			"b.comment",
			"b.editor_comment",
			"COALESCE(jsonb_agg(jsonb_build_object('id', a.id, 'name', a.name) ORDER BY a.name) FILTER (WHERE a.id IS NOT NULL), '[]'::jsonb) AS authors",
		).
		From("book b").
		LeftJoin("genre g", dbx.NewExp("g.id = b.genre")).
		LeftJoin("endowment e", dbx.NewExp("e.id = b.endowment")).
		LeftJoin("authors_books ab", dbx.NewExp("ab.book_id = b.id")).
		LeftJoin("author a", dbx.NewExp("a.id = ab.author_id")).
		GroupBy(
			"b.id", "b.title", "b.number", "b.volume", "b.genre", "g.name",
			"b.endowment", "e.name", "b.manuscript", "b.print", "b.comment", "b.editor_comment",
		).
		OrderBy("b.id")
	return query
}
