package com.github.flextasker.core.domain.exception

import okio.IOException

class ApiException(message: String) : IOException(message)