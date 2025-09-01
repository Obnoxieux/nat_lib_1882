package db

import "sync"

type TestAuthorRepository struct {
	Lock sync.Mutex
}

func (repo *TestAuthorRepository) GetAllAuthors() []Author {
	return []Author{}
}

func (repo *TestAuthorRepository) GetSingleAuthorByID(id int64) Author {
	repo.Lock.Lock()
	defer repo.Lock.Unlock()

	return Author{
		Id:   9999,
		Name: "الأصفهاني، أبو الفرج",
	}
}
