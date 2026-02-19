git add .

read -p "[+] Commit message: " msg
git commit -m "$msg"

echo "Current branch:"
git branch --show-current

read -p "Are you sure to push? (y/n): " confirm
if [[ "$confirm" == "y" || "$confirm" == "Y" ]]; then
    git push -u origin main
else
    echo "Push cancelled."
fi
