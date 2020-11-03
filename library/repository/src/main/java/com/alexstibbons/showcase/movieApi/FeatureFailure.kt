package com.alexstibbons.showcase.movieApi

import com.alexstibbons.showcase.responses.Failure

sealed class MediaFailure : Failure.FeatureSpecificFailure() {
    object NoSuchMedia: FeatureSpecificFailure()
    object EmptyMediaList: FeatureSpecificFailure()
}