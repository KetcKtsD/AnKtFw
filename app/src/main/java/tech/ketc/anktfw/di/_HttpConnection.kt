package tech.ketc.anktfw.di

import java.net.HttpURLConnection
import java.net.URL

interface HttpURLConnectionFactory {
    fun create(url: String): HttpURLConnection
}

class DefaultHttpURLConnectionFactory : HttpURLConnectionFactory {
    override fun create(url: String): HttpURLConnection {
        url.matches("(https?|ftp)(://[-_.!~*'()a-zA-Z0-9;/?:@&=+$,%#]+)".toRegex())
        return (URL(url).openConnection() as HttpURLConnection).apply {
            allowUserInteraction = false
            requestMethod = "GET"
            instanceFollowRedirects = false
        }
    }
}