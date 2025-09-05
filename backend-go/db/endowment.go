package db

import "github.com/pocketbase/dbx"

type EndowmentRepository interface {
	GetSingleEndowmentByID(id int64) (EndowmentEntity, error)
	GetAllEndowments() ([]EndowmentEntity, error)
}

type PostgresEndowmentRepository struct {
	DB *dbx.DB
}

func (repo PostgresEndowmentRepository) GetSingleEndowmentByID(id int64) (EndowmentEntity, error) {
	var endowment EndowmentEntity
	query := repo.DB.
		Select("*").
		From(EndowmentTable).
		Where(dbx.NewExp("id = {:id}", dbx.Params{"id": id}))

	err := query.One(&endowment)
	if err != nil {
		return endowment, err
	}
	return endowment, nil
}

func (repo PostgresEndowmentRepository) GetAllEndowments() ([]EndowmentEntity, error) {
	var endowments []EndowmentEntity
	query := repo.DB.
		Select("*").
		From(EndowmentTable)

	err := query.All(&endowments)
	if err != nil {
		return endowments, err
	}
	return endowments, nil
}
