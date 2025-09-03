package db

import "github.com/pocketbase/dbx"

type GenreRepository interface {
	GetSingleGenreByID(id int64) (Genre, error)
	GetAllGenres() ([]Genre, error)
}

type PostgresGenreRepository struct {
	DB *dbx.DB
}

func (repo PostgresGenreRepository) GetSingleGenreByID(id int64) (Genre, error) {
	var genre Genre
	query := repo.DB.
		Select("*").
		From(GenreTable).
		Where(dbx.NewExp("id = {:id}", dbx.Params{"id": id}))

	err := query.One(&genre)
	if err != nil {
		return genre, err
	}
	return genre, nil
}

func (repo PostgresGenreRepository) GetAllGenres() ([]Genre, error) {
	var genres []Genre
	query := repo.DB.
		Select("*").
		From(GenreTable)

	err := query.All(&genres)
	if err != nil {
		return genres, err
	}
	return genres, nil
}
