package com.alexstibbons.showcase.responses

sealed class Failure {
    object ServerError: Failure()
    object NetworkConnection: Failure()

    abstract class FeatureSpecificFailure: Failure()
}
