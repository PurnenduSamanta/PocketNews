package com.purnendu.PocketNews.RoomDb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TrendingNewsTable")
data class TrendingNewsTableModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String,
    val newsUrl: String,
    val imageUrl: String,
    val date: String,
)

@Entity(tableName = "EntertainmentNewsTable")
data class EntertainmentNewsTableModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String,
    val newsUrl: String,
    val imageUrl: String,
    val date: String,
)

@Entity(tableName = "SportsNewsTable")
data class SportsNewsTableModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String,
    val newsUrl: String,
    val imageUrl: String,
    val date: String,
)

@Entity(tableName = "BusinessNewsTable")
data class BusinessNewsTableModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String,
    val newsUrl: String,
    val imageUrl: String,
    val date: String,
)

@Entity(tableName = "TechNewsTable")
data class TechNewsTableModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String,
    val newsUrl: String,
    val imageUrl: String,
    val date: String,
)

@Entity(tableName = "HealthNewsTable")
data class HealthNewsTableModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String,
    val newsUrl: String,
    val imageUrl: String,
    val date: String,
)

@Entity(tableName = "ScienceNewsTable")
data class ScienceNewsTableModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String,
    val newsUrl: String,
    val imageUrl: String,
    val date: String,
)

@Entity(tableName = "BookmarksTable")
data class BookmarksTableModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val newsUrl: String,
)
