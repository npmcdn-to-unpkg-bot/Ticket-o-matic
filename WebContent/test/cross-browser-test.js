module.exports = {
'Page title is correct': function (test) {
  test
    .open('../index.html')
    .assert.title().is('Ticket-o-Matic', 'It has title')
    .done();
}
};
