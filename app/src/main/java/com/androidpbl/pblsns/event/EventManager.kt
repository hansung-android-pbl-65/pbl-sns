package com.androidpbl.pblsns.event

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.common.collect.Sets
import java.lang.reflect.Method
import java.util.logging.Level
import java.util.logging.Logger

object EventManager {

    private val listeners : MutableSet<ListenerContent> = Sets.newConcurrentHashSet();

    fun registerListener(listener: Listener) {
        val methods : MutableSet<Method> = Sets.newHashSet();
        listener::class.java.declaredMethods.forEach { method ->
            method.annotations.forEach {
                if (it.annotationClass == EventHandler::class) {
                    methods.add(method);
                }
            }
        }

        val content = ListenerContent(listener, methods);
        listeners.add(content);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun callEvent(event: Event) {
        listeners.forEach { content ->
            content.getEventMethods(event).forEach { method ->
                method.isAccessible = true
                method.invoke(content.listener, event)
            }
        }
    }

    class ListenerContent constructor(val listener: Listener, private val methods: MutableSet<Method>) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun getEventMethods(event: Event) : MutableSet<Method> {
            val finds : MutableSet<Method> = Sets.newHashSet()
            methods.forEach {
                val params = it.parameters;
                if (params.size == 1 && params[0].type == event::class.java) {
                    finds.add(it)
                }
            }
            return finds
        }

    }

}