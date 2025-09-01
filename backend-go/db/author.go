package db

import "github.com/pocketbase/dbx"

type AuthorRepository interface {
	GetSingleAuthorByID(id int64) (Author, error)
	GetAllAuthors() ([]Author, error)
}

type PostgresAuthorRepository struct {
	DB *dbx.DB
}

func (repo PostgresAuthorRepository) GetSingleAuthorByID(id int64) (Author, error) {
	var author Author
	query := repo.DB.
		Select("*").
		From(AuthorTable).
		Where(dbx.NewExp("id = {:id}", dbx.Params{"id": id}))

	err := query.One(&author)
	if err != nil {
		return author, err
	}
	return author, nil
}

func (repo PostgresAuthorRepository) GetAllAuthors() ([]Author, error) {
	var authors []Author
	query := repo.DB.
		Select("*").
		From(AuthorTable)

	err := query.All(&authors)
	if err != nil {
		return authors, err
	}
	return authors, nil
}
