package com.puskal.data.repository.camermedia

import com.puskal.data.model.TemplateModel
import com.puskal.data.source.TemplateDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Puskal Khadka on 4/3/2023.
 */
class TemplateRepository @Inject constructor() {
    fun getTemplates(): Flow<List<TemplateModel>> {
        return TemplateDataSource.fetchTemplates()
    }
}