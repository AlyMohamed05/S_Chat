package com.silverbullet.core.network.utils

object ApiErrorCodes {

    const val UnexpectedErrorCode = 0

    const val InvalidRequestBodyCode = 1

    // Authentication routes Codes
    const val UsernameAlreadyExistsCode = 2

    const val UserNotFoundCode = 3

    const val InvalidCredentialsCode = 4

    const val InvalidRefreshTokenCode = 5

    const val InvalidUsername = 6

    // Channel routes Codes
    const val AlreadyHaveChannelCode = 7

    const val NoChannelIdFoundCode = 8

    const val NoCommonChannelBetweenUsersCode = 9

    // Messages routes Codes
    const val MESSAGE_NOT_FOUND = 10

    // Connections routes Codes
    const val AlreadyConnectedUsers = 11

}