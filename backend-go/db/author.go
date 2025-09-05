package db

import "github.com/pocketbase/dbx"

type AuthorRepository interface {
	GetSingleAuthorByID(id int64) (AuthorEntity, error)
	GetAllAuthors() ([]AuthorEntity, error)
}

type PostgresAuthorRepository struct {
	DB *dbx.DB
}

func (repo PostgresAuthorRepository) GetSingleAuthorByID(id int64) (AuthorEntity, error) {
	var author AuthorEntity
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

func (repo PostgresAuthorRepository) GetAllAuthors() ([]AuthorEntity, error) {
	var authors []AuthorEntity
	query := repo.DB.
		Select("*").
		From(AuthorTable)

	err := query.All(&authors)
	if err != nil {
		return authors, err
	}
	return authors, nil
}
