<template>
  <Markdown :source="source" :plugins="plugins" class="markdown-body"/>
</template>

<script>
import Markdown from 'vue3-markdown-it';
import MarkdownItKatex from 'markdown-it-katex'
import 'highlight.js/styles/github.css'
import 'katex/dist/katex.min.css'
import 'github-markdown-css/github-markdown-light.css'
export default {
  name: "MarkShow",
  components: {
    Markdown
  },
  props: {
    source: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      plugins: [
        {plugin: MarkdownItKatex},
      ]
    }
  },
}
</script>

<style scoped>

</style>