package com.alexstibbons.showcase.movieApi

import com.alexstibbons.showcase.responses.Failure

sealed class MovieFailure : Failure.FeatureSpecificFailure() {
    object NoSuchMovie: FeatureSpecificFailure()
    object EmptyMovieList: FeatureSpecificFailure()
}