<template>
  <ElIcon :size="size2Number" :color="color">
    <!-- 动态组件 -->
    <component :is="currentIcon"></component>
  </ElIcon>
</template>

<script>
export default {
  name: 'EIcon'
}
</script>

<script setup>
import { computed } from 'vue'
import { ElIcon } from 'element-plus'
import * as Icons from '@element-plus/icons-vue' // 【步骤1】全量引入svg图标
import 'element-plus/es/components/icon/style/css' // 【步骤2】全量引入svg图标

// 【步骤3】接收父组件传入的 size、color 和 type
const props = defineProps({
  size: { // 图标尺寸
    type: [Number, String],
    default: 20,
    validator(value) {
      return !isNaN(value)
    }
  },
  color: { // 图标颜色
    type: String,
    default: 'inherit'
  },
  type: { // 图标类型
    type: String,
    default: ''
  }
})

// 【步骤4】size转换成数值型
const size2Number = computed(() => {
  if (isNaN(props.size)) {
    return 20
  }
  return Number(props.size)
})

// 【步骤6】动态获取图标
const currentIcon = computed(() => {
  let iconType = props.type
  let icon = null

  if (iconType !== '') {
    // 【步骤5】首字母大写
    icon = iconType.replace(iconType[0],iconType[0].toUpperCase())
  }

  return Icons[icon] // 通过中括号的方式动态获取
})
</script>