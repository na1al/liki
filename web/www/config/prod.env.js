'use strict'

const merge = require('webpack-merge')
const localEnv = require('./local.env')

module.exports = merge({
  NODE_ENV: '"production"',
  GOOGLE_MAP_API_KEY: '""'
}, localEnv);
