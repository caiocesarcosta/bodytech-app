package com.example.bodytech.repository.company.remote

import com.example.bodytech.model.company.Company

/**
 * Interface para operações de repositório de dados de [Company] (Empresa).
 * Define as operações de CRUD e a função de criação a partir de JSON.
 */
interface CompanyRepository {
    /**
     * Cria todas as empresas a partir de um arquivo JSON.
     * Ideal para preencher o banco de dados com dados de teste/iniciais.
     * @return [Result.success] se todas as empresas forem criadas com sucesso, [Result.failure] caso contrário.
     */
    suspend fun createAllCompaniesFromJson(): Result<Unit>

    /**
     * Cria um novo documento de empresa no Firestore.
     * @param company O objeto [Company] a ser criado. O 'id' da empresa será usado como ID do documento.
     * @return [Result.success] com a empresa criada se a operação for bem-sucedida, [Result.failure] caso contrário.
     */
    suspend fun createCompany(company: Company): Result<Company>

    /**
     * Obtém um documento de empresa pelo seu ID.
     * @param companyId O ID da empresa a ser buscada.
     * @return [Result.success] com o objeto [Company] se encontrado, [Result.failure] se não encontrado ou ocorrer um erro.
     */
    suspend fun getCompany(companyId: String): Result<Company>

    /**
     * Atualiza um documento de empresa existente no Firestore.
     * @param companyId O ID da empresa a ser atualizada.
     * @param company O objeto [Company] com os dados atualizados. O 'id' deve corresponder ao companyId.
     * @return [Result.success] com a empresa atualizada se a operação for bem-sucedida, [Result.failure] caso contrário.
     */
    suspend fun updateCompany(companyId: String, company: Company): Result<Company>

    /**
     * Deleta um documento de empresa do Firestore.
     * @param companyId O ID da empresa a ser deletada.
     * @return [Result.success] se a empresa for deletada com sucesso, [Result.failure] caso contrário.
     */
    suspend fun deleteCompany(companyId: String): Result<Unit>
}