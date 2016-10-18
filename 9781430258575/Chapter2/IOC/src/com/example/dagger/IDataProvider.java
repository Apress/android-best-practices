package com.example.dagger;

import java.util.List;

interface IDataProvider {
	void addTask(final String title);
	void deleteAll();
	void deleteTask(final long id);
	void deleteTask(final String title);
	List<String> findAll();
}