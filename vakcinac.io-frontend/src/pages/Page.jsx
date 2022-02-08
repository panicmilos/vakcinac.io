import { defineComponent, h } from "@vue/runtime-core";
import useCounter from "../compositions/useCounter";
import VueComponent from "../components/VueComponent.vue"

export default defineComponent(() => {
  const { counter, incrementCounter } = useCounter();

  return () => (
    <div>
      <button onClick={incrementCounter}>
        From the Vue: {counter.value}
      </button>
      <VueComponent />
    </div>
  );
})