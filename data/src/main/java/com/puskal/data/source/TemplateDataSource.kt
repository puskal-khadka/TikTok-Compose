package com.puskal.data.source

import com.puskal.data.model.TemplateModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Puskal Khadka on 4/3/2023.
 */
object TemplateDataSource {
    private val templates = listOf(
        TemplateModel(
            id = 2001,
            name = "Urban Mode",
            hint = "Please upload 1-2 photos",
            mediaUrl = "img1.jpg"
        ),
        TemplateModel(
            id = 2002,
            name = "Ocean Filter",
            hint = "Please upload 1-5 photos",
            mediaUrl = "img2.jpg"
        ),
        TemplateModel(
            id = 2003,
            name = "HyperSpeed Galaxy",
            hint = "Upload 1-3 photos",
            mediaUrl = "img3.jpg"
        ),
        TemplateModel(
            id = 2004,
            name = "Day in the Life",
            hint = "Upload 2-6 photos with a subject",
            mediaUrl = "img4.jpg"
        ),

        TemplateModel(
            id = 2005,
            name = "Manga Transition",
            hint = "Upload 2 photos to show the change",
            mediaUrl = "img5.jpg"
        ),
        TemplateModel(
            id = 2006,
            name = "Black & White",
            hint = "Please upload 1-6 photos",
            mediaUrl = "img6.jpg"
        ),
        TemplateModel(
            id = 2007,
            name = "Paint Transition",
            hint = "Upload 4 photos",
            mediaUrl = "img7.jpg"
        ),

        )

    fun fetchTemplates(): Flow<List<TemplateModel>> = flow {
        emit(templates.shuffled())
    }
}