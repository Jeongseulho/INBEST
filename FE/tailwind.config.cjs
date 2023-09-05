/**
 * @type {import('tailwindcss').Config}
 */
module.exports = {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      fontFamily: {
        light: ["light"],
        regular: ["regular"],
        medium: ["medium"],
        semiBold: ["semibold"],
        bold: ["bold"],
        extraBold: ["extraBold"],
      },
    },
  },
  plugins: [],
};
