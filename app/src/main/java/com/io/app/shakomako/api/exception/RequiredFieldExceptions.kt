package com.io.app.shakomako.api.exception

class RequiredFieldExceptions : Exception {
    constructor(s: String?) : super(s) {}
    constructor() : super("All fields are required!") {}
}