async function getKeyMaterial() {
  const secretKey = import.meta.env.VITE_HASH_SECRET;
  const encoder = new TextEncoder();
  const hash = await window.crypto.subtle.digest(
    {
      name: import.meta.env.VITE_TYPE_HASH,
    },
    encoder.encode(secretKey)
  );
  return new Uint8Array(hash).slice(0, 16);
}

export async function decrypt(ciphertext) {
  ciphertext = ciphertext.replace(/-/g, "+").replace(/_/g, "/");
  const decoder = new TextDecoder();
  const keyMaterial = await getKeyMaterial();
  const key = await window.crypto.subtle.importKey(
    "raw",
    keyMaterial,
    { name: "AES-CBC" },
    false,
    ["decrypt"]
  );
  const iv = new Uint8Array(16);
  const encryptedData = Uint8Array.from(atob(ciphertext), (c) =>
    c.charCodeAt(0)
  );
  const decrypted = await window.crypto.subtle.decrypt(
    {
      name: "AES-CBC",
      iv,
    },
    key,
    encryptedData
  );
  return decoder.decode(decrypted);
}

export async function encrypt(plaintext) {
  const encoder = new TextEncoder();
  const keyMaterial = await getKeyMaterial();
  const key = await window.crypto.subtle.importKey(
    "raw",
    keyMaterial,
    { name: "AES-CBC" },
    false,
    ["encrypt"]
  );
  const iv = new Uint8Array(16);
  const encrypted = await window.crypto.subtle.encrypt(
    {
      name: "AES-CBC",
      iv,
    },
    key,
    encoder.encode(plaintext)
  );
  return window
    .btoa(String.fromCharCode.apply(null, new Uint8Array(encrypted)))
    .replace(/\+/g, "-")
    .replace(/\//g, "_")
    .replace(/=+$/, "");
}
