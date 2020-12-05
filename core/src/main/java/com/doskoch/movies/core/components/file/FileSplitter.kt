package com.doskoch.movies.core.components.file

import com.extensions.rx.functions.applyIfActive
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import java.io.File

class FileSplitter {

    fun chunksCount(fileSize: Int, chunkSize: Int): Int {
        return if(fileSize.rem(chunkSize) > 0) {
            fileSize / chunkSize + 1
        } else {
            fileSize / chunkSize
        }
    }

    fun split(file: File, chunkSize: Int): Flowable<ByteArray> {
        return Flowable.create({ emitter ->
            try {
                file.inputStream().use { stream ->
                    while(stream.available() > 0) {
                        ByteArray(chunkSize).let { chunk ->
                            stream.read(chunk)
                            emitter.applyIfActive { onNext(chunk) }
                        }
                    }

                    emitter.setCancellable { stream.close() }
                }

                emitter.applyIfActive { onComplete() }
            } catch (t: Throwable) {
                emitter.applyIfActive { onError(t) }
            }
        }, BackpressureStrategy.BUFFER)
    }
}