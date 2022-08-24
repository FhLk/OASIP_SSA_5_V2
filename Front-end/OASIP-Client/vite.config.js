import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  base:'/ssa5/',
  server:{
    proxy:{
      '/api':{
        target:'http://oasip_Back_End:8080',
        changeOrigin:true,
        secure:false,
        rewrite:(path) => path.replace(/^\/api/,'')
      }
    }
  }
})
