'use strict'

const Game = require('../game.js')

describe('The test environment', () => {
  it('should pass', () => {
    expect(true).toBeTruthy();
  })

  it('should access game', () => {
    expect(Game).toBeDefined();
  })
})
