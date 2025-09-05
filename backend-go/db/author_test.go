package db

import "sync"

type TestAuthorRepository struct {
	Lock sync.Mutex
}

func (repo *TestAuthorRepository) GetAllAuthors() []AuthorEntity {
	return []AuthorEntity{}
}

func (repo *TestAuthorRepository) GetSingleAuthorByID(id int64) AuthorEntity {
	repo.Lock.Lock()
	defer repo.Lock.Unlock()

	return AuthorEntity{
		Id:   9999,
		Name: "الأصفهاني، أبو الفرج",
	}
}
