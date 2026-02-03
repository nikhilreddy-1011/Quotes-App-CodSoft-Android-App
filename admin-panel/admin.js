// Admin Panel JavaScript

let currentUser = null;

// Check auth state on load
auth.onAuthStateChanged((user) => {
    if (user) {
        currentUser = user;
        showAdminPanel();
        loadQuotes();
        loadStats();
    } else {
        showAuthSection();
    }
});

// Login function
function login() {
    const email = document.getElementById('emailInput').value;
    const password = document.getElementById('passwordInput').value;
    const errorEl = document.getElementById('authError');

    auth.signInWithEmailAndPassword(email, password)
        .then(() => {
            errorEl.textContent = '';
        })
        .catch((error) => {
            errorEl.textContent = error.message;
        });
}

// Logout function
function logout() {
    auth.signOut();
}

// Show/hide sections
function showAuthSection() {
    document.getElementById('authSection').style.display = 'block';
    document.getElementById('adminSection').style.display = 'none';
}

function showAdminPanel() {
    document.getElementById('authSection').style.display = 'none';
    document.getElementById('adminSection').style.display = 'block';
}

// Add new quote
function addQuote(event) {
    event.preventDefault();

    const quote = {
        text: document.getElementById('quoteText').value,
        author: document.getElementById('quoteAuthor').value || null,
        category: document.getElementById('quoteCategory').value,
        emoji: document.getElementById('quoteEmoji').value || null,
        backgroundColor: document.getElementById('quoteColor').value,
        isEnabled: document.getElementById('quoteEnabled').checked,
        isFavorite: false,
        lastShownDate: 0,
        createdAt: Date.now(),
        updatedAt: Date.now()
    };

    db.collection('quotes').add(quote)
        .then(() => {
            alert('Quote added successfully!');
            document.getElementById('quoteForm').reset();
            document.getElementById('quoteColor').value = '#F5F5F5';
            loadQuotes();
            loadStats();
        })
        .catch((error) => {
            alert('Error adding quote: ' + error.message);
        });
}

// Load quotes from Firestore
function loadQuotes() {
    db.collection('quotes')
        .orderBy('createdAt', 'desc')
        .get()
        .then((querySnapshot) => {
            const quotesListEl = document.getElementById('quotesList');
            quotesListEl.innerHTML = '';

            if (querySnapshot.empty) {
                quotesListEl.innerHTML = '<p style="text-align: center; color: #999;">No quotes yet. Add your first quote!</p>';
                return;
            }

            querySnapshot.forEach((doc) => {
                const quote = doc.data();
                const quoteEl = createQuoteElement(doc.id, quote);
                quotesListEl.appendChild(quoteEl);
            });
        })
        .catch((error) => {
            console.error('Error loading quotes:', error);
        });
}

// Create quote element
function createQuoteElement(id, quote) {
    const div = document.createElement('div');
    div.className = 'quote-item';
    div.style.borderLeftColor = quote.backgroundColor || '#87CEEB';

    const categoryEmojis = {
        'MOTIVATION': '💪',
        'SUCCESS': '🏆',
        'LIFE': '🌟',
        'LOVE': '❤️',
        'STUDY': '📚',
        'FITNESS': '🏃',
        'SAD': '😢',
        'HAPPY': '😊'
    };

    div.innerHTML = `
        <h4>${quote.emoji || ''} ${quote.text}</h4>
        <p>${quote.author ? '— ' + quote.author : 'Unknown'}</p>
        <div class="quote-meta">
            <span>${categoryEmojis[quote.category]} ${quote.category}</span>
            <span>${quote.isEnabled ? '✅ Active' : '❌ Disabled'}</span>
            <span>🎨 ${quote.backgroundColor}</span>
        </div>
        <div class="quote-actions">
            <button onclick="toggleQuote('${id}', ${!quote.isEnabled})" class="btn-secondary">
                ${quote.isEnabled ? 'Disable' : 'Enable'}
            </button>
            <button onclick="deleteQuote('${id}')" class="btn-danger">Delete</button>
        </div>
    `;

    return div;
}

// Toggle quote enabled status
function toggleQuote(id, enabled) {
    db.collection('quotes').doc(id).update({
        isEnabled: enabled,
        updatedAt: Date.now()
    })
        .then(() => {
            loadQuotes();
            loadStats();
        })
        .catch((error) => {
            alert('Error updating quote: ' + error.message);
        });
}

// Delete quote
function deleteQuote(id) {
    if (!confirm('Are you sure you want to delete this quote?')) {
        return;
    }

    db.collection('quotes').doc(id).delete()
        .then(() => {
            loadQuotes();
            loadStats();
        })
        .catch((error) => {
            alert('Error deleting quote: ' + error.message);
        });
}

// Load statistics
function loadStats() {
    db.collection('quotes').get()
        .then((querySnapshot) => {
            const total = querySnapshot.size;
            let active = 0;

            querySnapshot.forEach((doc) => {
                if (doc.data().isEnabled) {
                    active++;
                }
            });

            document.getElementById('totalQuotes').textContent = total;
            document.getElementById('activeQuotes').textContent = active;
        });
}

// Filter quotes by category
function filterQuotes() {
    const category = document.getElementById('filterCategory').value;

    let query = db.collection('quotes').orderBy('createdAt', 'desc');

    if (category) {
        query = query.where('category', '==', category);
    }

    query.get()
        .then((querySnapshot) => {
            const quotesListEl = document.getElementById('quotesList');
            quotesListEl.innerHTML = '';

            if (querySnapshot.empty) {
                quotesListEl.innerHTML = '<p style="text-align: center; color: #999;">No quotes found.</p>';
                return;
            }

            querySnapshot.forEach((doc) => {
                const quote = doc.data();
                const quoteEl = createQuoteElement(doc.id, quote);
                quotesListEl.appendChild(quoteEl);
            });
        });
}

// Search quotes
function searchQuotes() {
    const searchTerm = document.getElementById('searchInput').value.toLowerCase();

    db.collection('quotes')
        .orderBy('createdAt', 'desc')
        .get()
        .then((querySnapshot) => {
            const quotesListEl = document.getElementById('quotesList');
            quotesListEl.innerHTML = '';

            let found = false;

            querySnapshot.forEach((doc) => {
                const quote = doc.data();
                const text = quote.text.toLowerCase();
                const author = (quote.author || '').toLowerCase();

                if (text.includes(searchTerm) || author.includes(searchTerm)) {
                    const quoteEl = createQuoteElement(doc.id, quote);
                    quotesListEl.appendChild(quoteEl);
                    found = true;
                }
            });

            if (!found) {
                quotesListEl.innerHTML = '<p style="text-align: center; color: #999;">No quotes found.</p>';
            }
        });
}
