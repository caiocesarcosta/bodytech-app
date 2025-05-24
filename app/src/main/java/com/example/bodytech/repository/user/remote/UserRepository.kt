package com.example.bodytech.repository.user.remote

import com.example.bodytech.model.user.User

/**
 * Interface para operações de repositório de dados de [User] (Usuário).
 * Define as operações de CRUD e a função de criação a partir de JSON.
 */
interface UserRepository {
    /**
     * Cria todos os usuários a partir de um arquivo JSON.
     * Ideal para preencher o banco de dados com dados de teste/iniciais.
     * @return [Result.success] se todos os usuários forem criados com sucesso, [Result.failure] caso contrário.
     */
    suspend fun createAllUsersFromJson(): Result<Unit>

    /**
     * Cria um novo documento de usuário no Firestore.
     * @param user O objeto [User] a ser criado. O 'id' do usuário será usado como ID do documento.
     * @return [Result.success] com o usuário criado se a operação for bem-sucedida, [Result.failure] caso contrário.
     */
    suspend fun createUser(user: User): Result<User>

    /**
     * Obtém um documento de usuário pelo seu ID.
     * @param userId O ID do usuário a ser buscado.
     * @return [Result.success] com o objeto [User] se encontrado, [Result.failure] se não encontrado ou ocorrer um erro.
     */
    suspend fun getUser(userId: String): Result<User>

    /**
     * Atualiza um documento de usuário existente no Firestore.
     * @param userId O ID do usuário a ser atualizado.
     * @param user O objeto [User] com os dados atualizados. O 'id' deve corresponder ao userId.
     * @return [Result.success] com o usuário atualizado se a operação for bem-sucedida, [Result.failure] caso contrário.
     */
    suspend fun updateUser(userId: String, user: User): Result<User>

    /**
     * Deleta um documento de usuário do Firestore.
     * @param userId O ID do usuário a ser deletado.
     * @return [Result.success] se o usuário for deletado com sucesso, [Result.failure] caso contrário.
     */
    suspend fun deleteUser(userId: String): Result<Unit>
}