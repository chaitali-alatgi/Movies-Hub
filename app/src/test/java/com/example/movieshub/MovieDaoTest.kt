package com.example.movieshub

@RunWith(AndroidJUnit4::class)
class WeatherLocationDaoTest {

    private lateinit var database: App

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            Database::class.java
        ).build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun GIVEN_no_user_WHEN_insert_location_THEN_exception_is_thrown() {
        try {
            val weatherLocation = WeatherLocationFactory.createCurrentLocationTest()
            database.weatherLocationDao().insertWeatherLocation(weatherLocation.toEntity())

            val weatherLocationList = database.weatherLocationDao().getAllWeatherLocationList()
            assert(weatherLocationList.isNotEmpty())
        } catch (e: SQLiteConstraintException) {
            assert(e is SQLiteConstraintException)
        }
    }

    @Test
    fun GIVEN_user_WHEN_insert_location_THEN_location_is_saved() {
        // Insertamos el usuario de test
        val userTest = UserFactory.createUserTest()
        database.userDao().insert(userTest.toEntity())

        val weatherLocation = WeatherLocationFactory.createCurrentLocationTest()
        database.weatherLocationDao().insertWeatherLocation(weatherLocation.toEntity())

        val weatherLocationList = database.weatherLocationDao().getAllWeatherLocationList()
        assert(weatherLocationList.isNotEmpty())
    }

    @Test
    fun GIVEN_user_WHEN_user_deleted_THEN_location_deleted() {
        // Insertamos el usuario de test
        val userTest = UserFactory.createUserTest()
        database.userDao().insert(userTest.toEntity())

        val weatherLocation = WeatherLocationFactory.createCurrentLocationTest()
        database.weatherLocationDao().insertWeatherLocation(weatherLocation.toEntity())

        val weatherLocationList = database.weatherLocationDao().getAllWeatherLocationList()
        assert(weatherLocationList.isNotEmpty())

        database.userDao().delete(userTest.toEntity())

        val weatherLocationListAfterDelete = database.weatherLocationDao().getAllWeatherLocationList()
        assert(weatherLocationListAfterDelete.isNotEmpty())
    }

    @Test
    fun GIVEN_user_and_location_WHEN_deleteAll_location_THEN_location_is_empty() {
        // Insertamos el usuario de test
        val userTest = UserFactory.createUserTest()
        database.userDao().insert(userTest.toEntity())

        val weatherLocation = WeatherLocationFactory.createCurrentLocationTest()
        database.weatherLocationDao().insertWeatherLocation(weatherLocation.toEntity())

        val weatherLocationList = database.weatherLocationDao().getAllWeatherLocationList()
        assert(weatherLocationList.isNotEmpty())

        database.weatherLocationDao().deleteAll()
        val weatherLocationListAfterDeleteAll = database.weatherLocationDao().getAllWeatherLocationList()
        assert(weatherLocationListAfterDeleteAll.isNotEmpty())
    }


}
