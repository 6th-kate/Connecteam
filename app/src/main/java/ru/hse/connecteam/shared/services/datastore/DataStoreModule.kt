package ru.hse.connecteam.shared.services.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.osipxd.security.crypto.createEncrypted
import ru.hse.connecteam.features.auth.data.ServerAuthRepository
import ru.hse.connecteam.features.auth.domain.AuthRepository
import ru.hse.connecteam.features.profile.data.ServerProfileRepository
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.features.tariffs.data.ServerTariffRepository
import ru.hse.connecteam.features.tariffs.domain.TariffDataRepository
import ru.hse.connecteam.shared.services.user.UserService
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class EncryptedDataStore

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class StateDataStore

    @Provides
    fun provideAuthenticationService(@EncryptedDataStore dataStore: DataStore<Preferences>): AuthenticationService {
        return AuthenticationService(dataStore)
    }

    @Provides
    fun provideUserStatePreferences(@StateDataStore dataStore: DataStore<Preferences>): UserStatePreferences {
        return UserStatePreferences(dataStore)
    }

    @Provides
    fun provideAuthRepository(
        authenticationService: AuthenticationService,
        userStatePreferences: UserStatePreferences
    ): AuthRepository {
        return ServerAuthRepository(authenticationService, userStatePreferences)
    }

    @Provides
    @Singleton
    fun provideUserService(authenticationService: AuthenticationService): UserService {
        return UserService(authenticationService)
    }

    @Provides
    fun provideProfileRepository(
        authenticationService: AuthenticationService,
        userService: UserService
    ): ProfileDataRepository {
        return ServerProfileRepository(authenticationService, userService)
    }

    @Provides
    fun provideTariffRepository(authenticationService: AuthenticationService): TariffDataRepository {
        return ServerTariffRepository(authenticationService)
    }

    @Singleton
    @Provides
    @EncryptedDataStore
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.createEncrypted {
            EncryptedFile.Builder(
                context,
                context.dataStoreFile("store.preferences_pb"),
                MasterKey
                    .Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build(),
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build()
        }
    }

    @Singleton
    @Provides
    @StateDataStore
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
            produceFile = { context.preferencesDataStoreFile("state_preferences") })
    }
}