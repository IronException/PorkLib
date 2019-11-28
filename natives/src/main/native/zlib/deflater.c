#include "../common.h"
#include "net_daporkchop_lib_natives_zlib_NativeDeflater.h"

#include "lib-zlib/zlib.h"

#include <stdlib.h>
#include <string.h>

#include <stdio.h>

typedef struct {
    z_stream stream;
    jlong srcLen;
    jlong dstLen;
} context_t;

static jfieldID ctxID;
static jfieldID readBytesID;
static jfieldID writtenBytesID;
static jfieldID finishedID;

void JNICALL Java_net_daporkchop_lib_natives_zlib_NativeDeflater_load(JNIEnv* env, jclass cla)  {
    ctxID          = (*env)->GetFieldID(env, cla, "ctx", "J");
    readBytesID    = (*env)->GetFieldID(env, cla, "readBytes", "J");
    writtenBytesID = (*env)->GetFieldID(env, cla, "writtenBytes", "J");
    finishedID     = (*env)->GetFieldID(env, cla, "finished", "Z");
}

jlong JNICALL Java_net_daporkchop_lib_natives_zlib_NativeDeflater_init(JNIEnv* env, jclass cla, jint level, jboolean nowrap)   {
    context_t* context = (context_t*) malloc(sizeof(context_t));
    memset(context, 0, sizeof(context_t));
    int ret = deflateInit(&context->stream, level);

    if (ret != Z_OK)    {
        free(context);
        throwException(env, "Couldn't init deflater!", ret);
    }

    return (jlong) context;
}

void JNICALL Java_net_daporkchop_lib_natives_zlib_NativeDeflater_end(JNIEnv* env, jclass cla, jlong ctx)  {
    context_t* context = (context_t*) ctx;
    int ret = deflateEnd(&context->stream);
    free(context);

    if (ret != Z_OK)    {
        throwException(env, "Couldn't end deflater!", ret);
    }
}

void JNICALL Java_net_daporkchop_lib_natives_zlib_NativeDeflater_input(JNIEnv* env, jobject obj, jlong srcAddr, jlong srcLen) {
    context_t* context = (context_t*) (*env)->GetLongField(env, obj, ctxID);

    //context->srcAddr = srcAddr;
    context->stream.next_in = (unsigned char*) srcAddr;
    context->srcLen = srcLen;
}

void JNICALL Java_net_daporkchop_lib_natives_zlib_NativeDeflater_output(JNIEnv* env, jobject obj, jlong dstAddr, jlong dstLen)    {
    context_t* context = (context_t*) (*env)->GetLongField(env, obj, ctxID);

    //context->dstAddr = dstAddr;
    context->stream.next_out = (unsigned char*) dstAddr;
    context->dstLen = dstLen;
}

void JNICALL Java_net_daporkchop_lib_natives_zlib_NativeDeflater_deflate(JNIEnv* env, jobject obj, jboolean finish)  {
    context_t* context = (context_t*) (*env)->GetLongField(env, obj, ctxID);

    unsigned int avail_in  = context->stream.avail_in =  (unsigned int) min_l(context->srcLen, (jlong) 0xFFFFFFFF);
    unsigned int avail_out = context->stream.avail_out = (unsigned int) min_l(context->dstLen, (jlong) 0xFFFFFFFF);

    int ret = deflate(&context->stream, finish ? Z_FINISH : Z_NO_FLUSH);
    if (ret == Z_STREAM_END)    {
        (*env)->SetBooleanField(env, obj, finishedID, (jboolean) 1);
    } else if (ret != Z_OK)    {
        throwException(env, "Invalid return value from deflate()!", ret);
        return;
    }

    (*env)->SetLongField(env, obj, readBytesID,    (jlong) (avail_in - context->stream.avail_in));
    (*env)->SetLongField(env, obj, writtenBytesID, (jlong) (avail_out - context->stream.avail_out));
}

void JNICALL Java_net_daporkchop_lib_natives_zlib_NativeDeflater_reset(JNIEnv* env, jobject obj)     {
    context_t* context = (context_t*) (*env)->GetLongField(env, obj, ctxID);
    int ret = deflateReset(&context->stream);
    memset(context + sizeof(z_stream), 0, sizeof(context_t) - sizeof(z_stream));

    if (ret != Z_OK)    {
        throwException(env, "Couldn't reset deflater!", ret);
    }

    (*env)->SetBooleanField(env, obj, finishedID, (jboolean) 0);
}
