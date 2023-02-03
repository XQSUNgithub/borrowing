<template>
    <el-select
        v-model="value"
        filterable
        remote
        reserve-keyword
        :placeholder="placeholder"
        remote-show-suffix
        :remote-method="remoteMethod"
        :loading="loading"
        :disabled="disabled"
    >
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
    </el-select>
</template>
  
<script setup>
import { ref,computed } from 'vue'
import { ElOption, ElSelect } from 'element-plus';

const props = defineProps({
    option:Object,
    placeholder:String,
    disabled:Boolean,
    modelValue:String
});

const emit = defineEmits();

const options = ref([])
const value = computed({
    get(){
        return props.modelValue;
    },
    set(value){
        emit("update:modelValue",value);
    }
});
const loading = ref(false)

const remoteMethod = (query) => {
    if (query) {
        loading.value = true
        setTimeout(() => {
            loading.value = false
            options.value = props.option.filter((item) => {
                return (item.label+" "+item.value).toLowerCase().includes(query.toLowerCase());
            });
        }, 200)
    } else {
        options.value = []
    }
}

</script>
  