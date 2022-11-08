module.exports = {
  mode: 'jit',
  content: [
    './resources/**/*.html',
    './resources/**/*.js'
  ],
  theme: {
    extend: {},
  },
  variants: {},
  plugins: [],
  safelist: [
    'tile-0',
    'tile-2',
    'tile-4',
    'tile-8',
    'tile-16',
    'tile-32',
    'tile-64',
    'tile-128',
    'tile-256',
    'tile-512',
    'tile-1024',
    'tile-2048',
    'tile-4096',
  ]
}
