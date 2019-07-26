使い方の例

```kotlin
class SampleActivity : AppCompatActivity(), LifecycleScopeSupport {
    override val scope = LifecycleScope(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentViewとかごにょごにょ
        bindLaunch { 
            //asyncAwaitとかいろいろ
        }
    }
}

```

`bindLaunch()`を呼ぶだけで`onDestroy()`時にキャンセルされる`Coroutine`を作れる｡
