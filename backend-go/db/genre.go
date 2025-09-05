package db

import "github.com/pocketbase/dbx"

type GenreRepository interface {
	GetSingleGenreByID(id int64) (GenreEntity, error)
	GetAllGenres() ([]GenreEntity, error)
}

type PostgresGenreRepository struct {
	DB *dbx.DB
}

func (repo PostgresGenreRepository) GetSingleGenreByID(id int64) (GenreEntity, error) {
	var genre GenreEntity
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

func (repo PostgresGenreRepository) GetAllGenres() ([]GenreEntity, error) {
	var genres []GenreEntity
	query := repo.DB.
		Select("*").
		From(GenreTable)

	err := query.All(&genres)
	if err != nil {
		return genres, err
	}
	return genres, nil
}
