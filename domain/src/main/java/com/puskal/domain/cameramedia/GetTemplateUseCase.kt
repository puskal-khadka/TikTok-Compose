package com.puskal.domain.cameramedia

import com.puskal.data.model.TemplateModel
import com.puskal.data.repository.camermedia.TemplateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Puskal Khadka on 4/3/2023.
 */
class GetTemplateUseCase @Inject constructor(
    private val templateRepository: TemplateRepository
) {
    operator fun invoke(): Flow<List<TemplateModel>> {
        return templateRepository.getTemplates()
    }
}